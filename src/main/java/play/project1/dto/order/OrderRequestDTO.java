package play.project1.dto.order;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderRequestDTO {

	@Email
	@NotNull @NotBlank
	private String memberId;

	private List<OrderDetailRequestDTO> orderMenuList;
}
