package play.project1.dto.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberSaveDTO {

	@NotNull
	@NotBlank
	@Email
	private final String id;

	@NotNull
	@NotBlank
	private final String name;
}
