package play.project1.dto.menu;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuSaveDTO {

	@NotNull
	private final String name;

	@NotNull
	private final Integer menuCode;
}
