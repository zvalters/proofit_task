package lv.proofit.ticketing.enums;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static lv.proofit.ticketing.constant.TestConstants.PRICE;
import static lv.proofit.ticketing.enums.AgeGroup.ADULT;
import static lv.proofit.ticketing.enums.AgeGroup.CHILD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Percentage.withPercentage;

class AgeGroupTest {

    @Test
    void adultPriceApplied() {
        BigDecimal result = ADULT.applyDiscount(PRICE);

        assertThat(result).isEqualTo(PRICE);
    }

    @Test
    void childPriceApplied() {
        BigDecimal result = CHILD.applyDiscount(PRICE);

        assertThat(result)
                .isCloseTo(PRICE.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP), withPercentage(0));
    }
}