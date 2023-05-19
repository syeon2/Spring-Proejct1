package play.project1.dto.order;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class OrderDetailRequestDTO {

	@NotNull
	private Long menuId;

	@Min(1) @NotNull
	private Integer count;

	public OrderDetailRequestDTO() {
	}

	public OrderDetailRequestDTO(Long menuId, Integer count) {
		this.menuId = menuId;
		this.count = count;
	}
}
