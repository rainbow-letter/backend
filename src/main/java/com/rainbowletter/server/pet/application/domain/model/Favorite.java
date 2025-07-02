package com.rainbowletter.server.pet.application.domain.model;

import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Favorite {

    private final FavoriteId id;

    private int total;
    private int dayIncreaseCount;
    private boolean canIncrease;
    private LocalDateTime lastIncreasedAt;

    public static Favorite withId(
        final FavoriteId id,
        final int total,
        final int dayIncreaseCount,
        final boolean canIncrease,
        final LocalDateTime lastIncreasedAt
    ) {
        return new Favorite(id, total, dayIncreaseCount, canIncrease, lastIncreasedAt);
    }

    public static Favorite withoutId(
        final int total,
        final int dayIncreaseCount,
        final boolean canIncrease,
        final LocalDateTime lastIncreasedAt
    ) {
        return new Favorite(null, total, dayIncreaseCount, canIncrease, lastIncreasedAt);
    }

    public void increase(final LocalDateTime lastIncreasedAt) {
        validateIncrease(dayIncreaseCount);
        this.total++;
        this.dayIncreaseCount++;
        this.canIncrease = canIncrease(dayIncreaseCount);
        this.lastIncreasedAt = lastIncreasedAt;
    }

    private void validateIncrease(final int dayIncreaseCount) {
        if (!canIncrease(dayIncreaseCount)) {
            throw new RainbowLetterException("max.pet.like.count");
        }
    }

    private boolean canIncrease(final int dayIncreaseCount) {
        return dayIncreaseCount < 3;
    }

    public record FavoriteId(Long value) {

        @Override
        public String toString() {
            return value.toString();
        }

    }

}
