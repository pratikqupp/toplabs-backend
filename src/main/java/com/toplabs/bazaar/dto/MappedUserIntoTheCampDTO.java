package  com.toplabs.bazaar.dto;


import com.toplabs.bazaar.Enum.AppRole;
import com.toplabs.bazaar.Enum.LabDepartment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MappedUserIntoTheCampDTO {
    private String campId;
    private String userId;
    private LabDepartment department;
    private AppRole designation;
}
