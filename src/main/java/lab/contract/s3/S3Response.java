package lab.contract.s3;

import lombok.*;

import javax.validation.Valid;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Valid
public class S3Response {
    private int numOfPage;
    private Object[] url;

    @Builder
    public S3Response(int numOfPage, Object[] url) {
        this.numOfPage = numOfPage;
        this.url = url;
    }
}
