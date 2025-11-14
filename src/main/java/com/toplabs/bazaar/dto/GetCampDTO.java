package com.toplabs.bazaar.dto;

import com.toplabs.bazaar.Enum.LocalCampStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetCampDTO {
    private LocalCampStatus campStatus;
    private LocalDateTime dateTime;
}
