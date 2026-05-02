package cn.edu.sjziei.lms.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDisPatchListVo {
    Long total;
    Integer pages;
    Integer current;
    List<DisPatchListVo> records;
}
