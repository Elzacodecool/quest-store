function createTiles(tiles) {

	var TITLE_INDEX = 0;
	var TILE_TYPE_INDEX = 1;
	var IMAGE_CLASS_INDEX = 2;
	var LINK_INDEX = 3;

	var mainDiv = document.getElementById("content-container");
	for (var i = 0; i < tiles.length; i++) {
		
		var tile = document.createElement("div");

		var className = getClassName(tiles[i][TILE_TYPE_INDEX]);
		tile.setAttribute("class", className);
		
		var a = document.createElement("a");
		a.setAttribute("class", "foreign-link");
		a.href = tiles[i][LINK_INDEX];
		tile.appendChild(a);

		var image = document.createElement("i");
		image.setAttribute("class", tiles[i][IMAGE_CLASS_INDEX]);
		a.appendChild(image);
		
		var paragraph = document.createElement("p");
		paragraph.innerHTML = tiles[i][TITLE_INDEX];
		a.appendChild(paragraph);
		
		mainDiv.appendChild(tile);
	}
}

function getClassName(tileType) {
	var USER = "User";
	var USER_CLASS = "user-tiles";

	var ARTIFACT = "Artifact";
	var ARTIFACT_CLASS = "artifact-tiles";

	var QUEST = "Quest";
	var QUEST_CLASS = "quest-tiles";

	var LOGOUT = "Logout";
	var LOGOUT_CLASS = "logout-tiles";

	var className = "tile ";

	if (tileType == USER) {
		className = className + USER_CLASS;
	} else if (tileType == ARTIFACT) {
		className = className + ARTIFACT_CLASS;
	} else if (tileType == QUEST) {
		className = className + QUEST_CLASS;
	} else if (tileType == LOGOUT) {
		className = className + LOGOUT_CLASS;
	}

	return className;
}