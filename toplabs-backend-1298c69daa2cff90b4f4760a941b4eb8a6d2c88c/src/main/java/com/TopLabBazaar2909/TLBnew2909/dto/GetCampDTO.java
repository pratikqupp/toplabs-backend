package com.TopLabBazaar2909.TLBnew2909.dto;

import com.TopLabBazaar2909.TLBnew2909.Enum.LocalCampStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetCampDTO {
    private LocalCampStatus campStatus;
    private LocalDateTime dateTime;
}
