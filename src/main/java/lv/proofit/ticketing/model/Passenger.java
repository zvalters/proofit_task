package lv.proofit.ticketing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.proofit.ticketing.enums.AgeGroup;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {

    @NotNull
    private AgeGroup ageGroup;
    @NotBlank
    private String destinationTerminalName;
    @Min(0)
    private int luggageAmount;

}
