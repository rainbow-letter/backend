package com.rainbowletter.server.user.application.domain.service;

import com.rainbowletter.server.common.application.port.out.PublishDomainEventPort;
import com.rainbowletter.server.common.util.TimeHolder;
import com.rainbowletter.server.log.application.port.in.LogEventCommand;
import com.rainbowletter.server.log.application.port.in.LogEventUseCase;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.DeleteUserPort;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class DeleteUserScheduler {

    private static final int USER_CLEAN_LEAVE_DAY = 7;

    private final TimeHolder timeHolder;
    private final PublishDomainEventPort publishDomainEventPort;

    private final LoadUserPort loadUserPort;
    private final DeleteUserPort deleteUserPort;
    private final LogEventUseCase logEventUseCase;

    @Async
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void hardDeleteUser() {
        final LocalDate currentDate = timeHolder.currentDate();
        final LocalDate beforeDate = currentDate.minusDays(USER_CLEAN_LEAVE_DAY + 1L);
        final LocalDateTime beforeDateTime = LocalDateTime.of(beforeDate, LocalTime.MAX);

        final List<LogEventCommand> logs = new ArrayList<>();
        final List<User> users = loadUserPort.loadUsersByLeaveAndBeforeDate(beforeDateTime);
        for (final User user : users) {
            logs.add(new LogEventCommand(
                    user.getId().value(),
                    user.getId().value(),
                    "USER",
                    "DELETE",
                    "%s에 탈퇴 요청 후 %d일이 경과하여 사용자 [%s] 데이터를 삭제합니다."
                        .formatted(user.getUpdatedAt(), USER_CLEAN_LEAVE_DAY, user.getEmail())
                )
            );

            user.delete();
            publishDomainEventPort.publish(user);
        }

        deleteUserPort.deleteAll(users);
        logEventUseCase.successLog(logs);
    }

}
