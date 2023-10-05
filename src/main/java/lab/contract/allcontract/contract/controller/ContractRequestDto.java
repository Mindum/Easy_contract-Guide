<<<<<<< HEAD
package lab.contract.allcontract.contract.controller;
=======
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7

package lab.contract.allcontract.contract.controller;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class ContractRequestDto {
    private Long userId;
    private MultipartFile pdfFile;
}