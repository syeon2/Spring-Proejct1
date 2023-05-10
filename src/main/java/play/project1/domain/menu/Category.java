package play.project1.domain.menu;

public enum Category {
	COFFEE(1);

	private final int menuCode;

	Category(int menuCode) {
		this.menuCode = menuCode;
	}

	public int getMenuCode() {
		return menuCode;
	}
}
