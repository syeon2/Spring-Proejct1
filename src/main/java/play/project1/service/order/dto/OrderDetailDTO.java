package play.project1.service.order.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderDetailDTO {

	private Long menuId;
	private Integer count;
}
