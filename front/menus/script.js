function changeColors() {
	if (document.body.title == "black") {
		document.body.style = "background-color: #FFFFFF; color: #000000;";
		document.body.title = "white";
	} else {
		document.body.style = "background-color: #303030; color: #F0F0F0;";
		document.body.title = "black";
	}
}

function createTiles(tiles) {

	var TITLE_INDEX = 0;
	var TILE_TYPE_INDEX = 1;
	var IMAGE_CLASS_INDEX = 2;

	var mainDiv = document.getElementById("content-container");
	for (var i = 0; i < tiles.length; i++) {
		
		var tile = document.createElement("div");

		var className = getClassName(tiles[i][TILE_TYPE_INDEX]);
		tile.setAttribute("class", className);

		var image = document.createElement("i");
		image.setAttribute("class", tiles[i][IMAGE_CLASS_INDEX]);
		tile.appendChild(image);
		
		var paragraph = document.createElement("p");
		paragraph.innerHTML = tiles[i][TITLE_INDEX];
		tile.appendChild(paragraph);
		
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