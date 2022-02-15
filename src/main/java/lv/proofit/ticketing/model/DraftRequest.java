package lv.proofit.ticketing.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class DraftRequest {

    @NotNull
    private List<Passenger> passengers;

}
