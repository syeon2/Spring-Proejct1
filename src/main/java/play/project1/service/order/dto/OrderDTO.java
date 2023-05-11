package play.project1.service.order.dto;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderDTO {

	private String memberId;
	private List<OrderDetailDTO> orderMenuList;
}
