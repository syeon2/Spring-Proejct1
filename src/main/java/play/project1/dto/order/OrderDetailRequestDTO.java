package play.project1.dto.order;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderDetailRequestDTO {

	@NotNull
	private Long menuId;

	@Min(1) @NotNull
	private Integer count;
}
