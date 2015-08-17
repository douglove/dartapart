<?php


// add player
// add player


// function: create game
// function: player join game
// function: player join game
// function: player join game

// start game

// loop while no winner
//   function: create round
//   loop while player active
//   next player
//     function: create throw
//     check for win
//     function: create throw
//     check for win
//     function: create throw
//     check for win



$action = $_GET['action'];


if ($action == 'login') {

   login($_GET['email'], $_GET['password']);

} else if ($action == "games") {

    printGameListXML();

} else if ($action == 'players') {

    printPlayerListXML();

} else if ($action == 'gameplayers') {

    printGamePlayerListXML($_GET['gameid']);

} else if ($action == "creategame") {

    createGame($_GET['name'], $_GET['type'], $_GET['status']);

} else if ($action == "gamestatus") {

    printGameStatus($_GET['gameid']);

} else if ($action == "updatestatus") {

    updateGameStatus($_GET['gameid'], $_GET['status'], $_GET['data']);

} else if ($action == "addthrow") {

    addThrow($_GET['gameid'], $_GET['playerid'], $_GET['x'], $_GET['y']);

} else if ($action == "joingame") {

    joinGame($_GET['gameid'], $_GET['playerid']);

} else if ($action == "throws") {

    printThrowsXML($_GET['gameid'], $_GET['throwid']);

} else {

    printGameListXML();

}


//createGame("Nicks 2nd game", 1);
//printGameListXML();

//createPlayer('Dlove', 'Doug Love', 'douglove@gmail.com', '3720 Van Court, West Richland, WA', '');
//printPlayerListXML();

//createRound(1);
//createThrow(1, 2, rand(0, 1000) / 1000, rand(0, 1000) / 1000);
//createThrow(1, 2, rand(0, 1000) / 1000, rand(0, 1000) / 1000);
//createThrow(1, 2, rand(0, 1000) / 1000, rand(0, 1000) / 1000);


//printPlayerThrowsXML(3, 1);

//printRoundXML(1);



//***********************************
//  Log a user in
//***********************************
function login($email, $password) {

    session_start();

    $link = connect();


    // Performing SQL query
    $query = "SELECT * FROM player WHERE email = '$email'";
    $result = mysql_query($query) or die('Query failed: ' . mysql_error());


    // Printing results in HTML
    echo "<?xml version=\"1.0\"?>\n";
    echo "<response>\n";
    if ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
        printf("<session>%s</session>\n", session_id());
        print(" <player>\n");
        printf("  <id>%s</id>\n", $row["player_id"]);
        printf("  <name>%s</name>\n", $row["name"]);
        printf("  <description>%s</description>\n", $row["description"]);
        printf("  <email>%s</email>\n", $row["email"]);
        printf("  <geoloc>%s</geoloc>\n", $row["geoloc"]);
        printf("  <icon>%s</icon>\n", $row["icon"]);
        printf("  <create_date>%s</create_date>\n", $row["create_date"]);
        printf("  <last_login_date>%s</last_login_date>\n", $row["last_login_date"]);
        print(" </player>\n");
    }
    echo "</response>\n";


    // Free resultset
    mysql_free_result($result);

    disconnect($link);
}



//***********************************
//  List the round data in XML
//***********************************
function printRoundXML($roundID) {

    $link = connect();


    // Performing SQL query
    $query = "SELECT player.player_id, throw.throw_id, throw.throw_date, throw.x, throw.y " .
             "FROM round, throw, player " .
             "WHERE round.round_id = throw.round_id " .
               "AND throw.player_id = player.player_id " .
               "AND round.round_id = $roundID";
    $result = mysql_query($query) or die('Query failed: ' . mysql_error());


    // Printing results in HTML
    echo "<?xml version=\"1.0\"?>\n";
    echo "<response>\n";

    $prevPlayerID = -1;
    while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {

        if ($prevPlayerID == -1 || $prevPlayerID != $row["player_id"]) {
            if ($prevPlayerID != -1) {
                 print(" </player>\n");
            }
            printf(" <player id=\"%s\">\n", $row["player_id"]);
        }

        print(" <throw>\n");
        printf("  <id>%s</id>\n", $row["throw_id"]);
        printf("  <throw_date>%s</throw_date>\n", $row["throw_date"]);
        printf("  <x>%s</x>\n", $row["x"]);
        printf("  <y>%s</y>\n", $row["y"]);
	    print(" </throw>\n");

        $prevPlayerID = $row["player_id"];
    }

    if ($prevPlayerID != -1) {
	    print(" </player>\n");
	}

    echo "</response>\n";


    // Free resultset
    mysql_free_result($result);

    disconnect($link);
}



//***********************************
//  List a players throws in a certain round XML
//***********************************
function printThrowsXML($gameID, $throwID) {

    $link = connect();


    // Performing SQL query
    $query = "SELECT * FROM throw WHERE game_id = $gameID and throw_id > $throwID ORDER BY throw_id";
    $result = mysql_query($query) or die('Query failed: ' . mysql_error());


    // Printing results in HTML
    echo "<?xml version=\"1.0\"?>\n";
    echo "<response>\n";
    while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
        print(" <throw>\n");
        printf("  <id>%s</id>\n", $row["throw_id"]);
        printf("  <throw_date>%s</throw_date>\n", $row["throw_date"]);
        printf("  <x>%s</x>\n", $row["x"]);
        printf("  <y>%s</y>\n", $row["y"]);
        print(" </throw>\n");
    }
    echo "</response>\n";


    // Free resultset
    mysql_free_result($result);

    disconnect($link);
}


//***********************************
//  Create throw
//***********************************
function createThrow($roundID, $playerID, $x, $y) {

    $link = connect();

    // Performing SQL query
//    $query = "SELECT count(*) as throw_count FROM throw WHERE round_id = $roundID and player_id = $playerID";
//    $result = mysql_query($query) or die('Query failed: ' . mysql_error());

//    $row = mysql_fetch_array($result, MYSQL_ASSOC)
//    $throwCount = $row["throw_count"];


//	if ($throwCount < 3) {

        // Free resultset
        mysql_free_result($result);


        mysql_query("INSERT INTO throw (round_id, player_id, throw_date, x, y) VALUES ($roundID, $playerID, Now(), $x, $y)")
            or die('Query failed: ' . mysql_error());
//    }

    disconnect($link);
}


//***********************************
//  Create a new player
//***********************************
function createRound($gameID) {

    $link = connect();

    mysql_query("INSERT INTO round (game_id) VALUES ($gameID)")
        or die('Query failed: ' . mysql_error());;

    disconnect($link);
}




//***********************************
//  Create a new player
//***********************************
function createPlayer($name, $description, $email, $geoloc, $icon) {

    $link = connect();

    mysql_query("INSERT INTO player (name, description, email, geoloc, icon, create_date, last_login_date) VALUES ('$name', '$description', '$email', '$geoloc', '$icon', Now(), Now())")
        or die('Query failed: ' . mysql_error());;

    disconnect($link);
}



//***********************************
//  Update game status
//***********************************
function updateGameStatus($gameID, $status, $data) {

    $link = connect();

    mysql_query("UPDATE game SET status = $status, shared_data = '$data' WHERE game_id = $gameID")
        or die('Query failed: ' . mysql_error());;

    disconnect($link);
}


//***********************************
//  List the players in XML
//***********************************
function printPlayerListXML() {

    $link = connect();


    // Performing SQL query
    $query = 'SELECT * FROM player';
    $result = mysql_query($query) or die('Query failed: ' . mysql_error());


    // Printing results in HTML
    echo "<?xml version=\"1.0\"?>\n";
    echo "<response>\n";
    while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
        print(" <player>\n");
        printf("  <id>%s</id>\n", $row["player_id"]);
        printf("  <name>%s</name>\n", $row["name"]);
        printf("  <description>%s</description>\n", $row["description"]);
        printf("  <email>%s</email>\n", $row["email"]);
        printf("  <geoloc>%s</geoloc>\n", $row["geoloc"]);
        printf("  <icon>%s</icon>\n", $row["icon"]);
        printf("  <create_date>%s</create_date>\n", $row["create_date"]);
        printf("  <last_login_date>%s</last_login_date>\n", $row["last_login_date"]);
        print(" </player>\n");
    }
    echo "</response>\n";


    // Free resultset
    mysql_free_result($result);

    disconnect($link);
}


//***********************************
//  List the players from a game in XML
//***********************************
function printGamePlayerListXML($gameID) {

    $link = connect();


    // Performing SQL query
    $query = "SELECT * FROM player, players WHERE player.player_id = players.player_id and players.game_id = $gameID";
    $result = mysql_query($query) or die('Query failed: ' . mysql_error());


    // Printing results in HTML
    echo "<?xml version=\"1.0\"?>\n";
    echo "<response>\n";
    while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
        print(" <player>\n");
        printf("  <id>%s</id>\n", $row["player_id"]);
        printf("  <name>%s</name>\n", $row["name"]);
        printf("  <description>%s</description>\n", $row["description"]);
        printf("  <email>%s</email>\n", $row["email"]);
        printf("  <geoloc>%s</geoloc>\n", $row["geoloc"]);
        printf("  <icon>%s</icon>\n", $row["icon"]);
        printf("  <create_date>%s</create_date>\n", $row["create_date"]);
        printf("  <last_login_date>%s</last_login_date>\n", $row["last_login_date"]);
        printf("  <join_order>%s</join_order>\n", $row["join_order"]);
        print(" </player>\n");
    }
    echo "</response>\n";


    // Free resultset
    mysql_free_result($result);

    disconnect($link);
}


//***********************************
//  Create a new game
//***********************************
function joinGame($gameID, $playerID) {

    $link = connect();


    $query = "SELECT count(*) as player_count FROM players WHERE game_id = $gameID AND player_id = $playerID";
    $result = mysql_query($query) or die('Query failed: ' . mysql_error());

    $row = mysql_fetch_array($result, MYSQL_ASSOC);
    $count = $row["player_count"];

    mysql_free_result($result);


    if ($count == 0) {

    	mysql_query("INSERT INTO players (game_id, player_id) VALUES ($gameID, $playerID)")
             or die('Query failed: ' . mysql_error());;
    }

    disconnect($link);
}



//***********************************
//  Create a new game
//***********************************
function addThrow($gameID, $playerID, $x, $y) {

    $link = connect();

	mysql_query("INSERT INTO throw (game_id, player_id, throw_date, x, y) VALUES ($gameID, $playerID, Now(), $x, $y)")
		 or die('Query failed: ' . mysql_error());

    disconnect($link);
}



//***********************************
//  Create a new game
//***********************************
function createGame($name, $type, $status) {

    $link = connect();

    mysql_query("INSERT INTO game (name, type_id, status, create_date, modify_date) VALUES ('$name', $type, $status, Now(), Now())")
        or die('Query failed: ' . mysql_error());;

	$gameID = mysql_insert_id($link);

    disconnect($link);

    printGameStatus($gameID);
}


//***********************************
//  List the games in XML
//***********************************
function printGameListXML() {

    // Performing SQL query
    $query = 'SELECT game.game_id, game.type_id, game.status, game.name, game.create_date, game.modify_date, GROUP_CONCAT(player.name SEPARATOR \', \') as players, COUNT(player.name) as player_count ' .
                  'FROM game LEFT JOIN (players, player) ON game.game_id = players.game_id AND players.player_id = player.player_id ' .
                  'GROUP BY game.game_id';
    printGameXML($query);

}




//***********************************
//  List the games in XML
//***********************************
function printGameStatus($gameID) {

    // Performing SQL query
    $query = 'SELECT game.game_id, game.type_id, game.status, game.name, game.create_date, game.modify_date, game.shared_data, GROUP_CONCAT(player.name SEPARATOR \', \') as players, COUNT(player.name) as player_count ' .
                  'FROM game LEFT JOIN (players, player) ON game.game_id = players.game_id AND players.player_id = player.player_id ' .
                  "WHERE game.game_id = $gameID " .
                  'GROUP BY game.game_id';
    printGameXML($query);

}


//***********************************
//  List the games in XML
//***********************************
function printGameXML($query) {

    $link = connect();


    // Performing SQL query
    $result = mysql_query($query) or die('Query failed: ' . mysql_error());


    // Printing results in HTML
    echo "<?xml version=\"1.0\"?>\n";
    echo "<response>\n";
    while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
        print(" <game>\n");
        printf("  <id>%s</id>\n", $row["game_id"]);
        printf("  <type>%s</type>\n", $row["type_id"]);
        printf("  <status>%s</status>\n", $row["status"]);
        printf("  <name>%s</name>\n", $row["name"]);
        printf("  <create_date>%s</create_date>\n", $row["create_date"]);
        printf("  <modify_date>%s</modify_date>\n", $row["modify_date"]);
        printf("  <players>%s</players>\n", $row["players"]);
        printf("  <count>%s</count>\n", $row["player_count"]);
        printf("  <data>%s</data>\n", $row["shared_data"]);
        print(" </game>\n");
    }
    echo "</response>\n";


    // Free resultset
    mysql_free_result($result);

    disconnect($link);

}




//***********************************
//  Connect to DB
//***********************************
function connect() {

    // Connecting, selecting database
    $link = mysql_connect('localhost', 'da', 'dartMagic') or die('Could not connect: ' . mysql_error());

    mysql_select_db('dartapart') or die('Could not select database');

    return $link;
}


//***********************************
//  Disconnect from DB
//***********************************
function disconnect($link) {

    // Closing connection
    mysql_close($link);
}

?>