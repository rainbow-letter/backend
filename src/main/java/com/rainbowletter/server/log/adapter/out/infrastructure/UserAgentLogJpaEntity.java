package com.rainbowletter.server.log.adapter.out.infrastructure;

import static lombok.AccessLevel.PROTECTED;

import com.rainbowletter.server.common.adapter.out.persistence.BaseTimeJpaEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "log_user_agent")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
class UserAgentLogJpaEntity extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String event;

    private String device;

    private String deviceName;

    private String os;

    private String osName;

    private String osVersion;

    private String agent;

    private String agentName;

    private String agentVersion;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final UserAgentLogJpaEntity data)) {
            return false;
        }
        return Objects.equals(id, data.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
