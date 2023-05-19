package play.project1.dto.order;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class OrderRequestDTO {

	@Email
	@NotNull @NotBlank
	private String memberId;

	private List<OrderDetailRequestDTO> orderMenuList;

	public OrderRequestDTO() {
	}

	public OrderRequestDTO(String memberId, List<OrderDetailRequestDTO> orderMenuList) {
		this.memberId = memberId;
		this.orderMenuList = orderMenuList;
	}
}
