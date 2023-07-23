package lab.contract.biz.contract_img.persistence.contractImgController;

import lab.contract.biz.contract_img.persistence.entity.ContractImg;
import lab.contract.biz.contract_img.persistence.service.ContractImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/contracts")
public class ContractImgController {

    private final ContractImgService contractImgService;

    @Autowired
    public ContractImgController(ContractImgService contractImgService) {
        this.contractImgService = contractImgService;
    }

    @GetMapping("/{contractId}/images")
    public ResponseEntity<List<ContractImg>> getContractImages(@PathVariable Long contractId) {
        List<ContractImg> contractImages = contractImgService.getImagesByContractId(contractId);
        return ResponseEntity.ok(contractImages);
    }

    @PostMapping("/{contractId}/images")
    public ResponseEntity<?> convertPdfToJpgAndSaveToDB(@PathVariable Long contractId, @RequestBody byte[] pdfData) {
        try {
            contractImgService.convertPdfToJpgAndSaveToDB(contractId, pdfData);
            return ResponseEntity.ok().build();
        } catch (IOException | ExecutionException | InterruptedException e) {
            return ResponseEntity.badRequest().body("Error converting PDF to JPG: " + e.getMessage());
        }
    }
}
