package app.backend.itemservice.item.dto.res;

public record ItemTestDto(
	String domainName,
	Long itemId,
	String testMessage
) {

	public static ItemTestDto of(String domainName, Long itemId, String testMessage) {
		return new ItemTestDto(domainName, itemId, testMessage);
	}

}
