function changeColors() {
	if (document.body.title == "black") {
		document.body.style = "background-color: #FFFFFF; color: #000000;";
		document.body.title = "white";
	} else {
		document.body.style = "background-color: #303030; color: #F0F0F0;";
		document.body.title = "black";
	}
}

function createTiles(tilesNamesArray) {

	var TITLE = 0;
	var TILE_TYPE = 1;

	var mainDiv = document.getElementById("content-container");
	for (var i = 0; i < tilesNamesArray.length; i++) {
		
		var tile = document.createElement("div");

		var className = getClassName(tilesNamesArray[i][TILE_TYPE]);
		tile.setAttribute("class", className);

		var image = document.createElement("i");
		image.setClass(tilesNamesArray[i][IMAGE_CLASS]);
		tile.appendChild(image);
		
		var paragraph = document.createElement("p");
		paragraph.innerHTML = tilesNamesArray[i][TITLE];
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