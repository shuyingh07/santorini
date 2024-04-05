package main.java.com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import fi.iki.elonen.NanoHTTPD;


public class App extends NanoHTTPD {

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    private Game game;
    private Board board;
    private Validator validator;
    private int setup_stage = 0;
    private static final int PORT = 8080;

    public App() throws IOException {
        super(PORT);

        this.game = new Game();
        this.board = new Board(5, 5);

        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning!\n");
    }

    @Override
    public Response serve(IHTTPSession session) {

        String uri = session.getUri();
        @SuppressWarnings("deprecation")
        Map<String, String> params = session.getParms();

        game.gameStart();
        //this.setWorkerId = game.getCurrState().getSetWorkerId();

        if (uri.equals("/test")) {
            System.out.println("Testing connection: server side received!");
            String myjsonstr = "{ \"test1\": \"" + 1 + "\"," +
                    " \"test2\": \"" + 0 + "\"," +
                    " \"test3\": \"" + 1 + "\"}";

            return newFixedLengthResponse(myjsonstr);
        } else if (uri.equals("/setup")) {
            System.out.println("App: Setup request received!");
            System.out.println(params.get("x"));
            System.out.println(params.get("y"));
            int x = Integer.parseInt(params.get("x"));
            int y = Integer.parseInt(params.get("y"));
            
            if (setup_stage == 0){
                Player currentPlayer = game.getCurrentPlayer();
                currentPlayer.chooseWorker(0);
                boolean init_result = currentPlayer.moveWorker(validator, board, x, y);
                setup_stage += 1;
            }
            else if (setup_stage == 1){
                Player currentPlayer = game.getCurrentPlayer();
                currentPlayer.chooseWorker(1);
                boolean init_result = currentPlayer.moveWorker(validator, board, x, y);
                setup_stage += 1;
            }
            else if (setup_stage == 2){
                game.changeCurrentPlayer();
                Player currentPlayer = game.getCurrentPlayer();
                currentPlayer.chooseWorker(0);
                boolean init_result = currentPlayer.moveWorker(validator, board, x, y);
                setup_stage += 1;
            }
            else if (setup_stage == 3){
                game.changeCurrentPlayer();
                Player currentPlayer = game.getCurrentPlayer();
                currentPlayer.chooseWorker(1);
                boolean init_result = currentPlayer.moveWorker(validator, board, x, y);
            }
            // if (state == null) {
            //     return newFixedLengthResponse(new State(game.getCurrState(), -1).toString());
            // }

            // return newFixedLengthResponse(state.toString());
        }
        // } else if (uri.equals("/choosemove")) {
        //     System.out.println("App: chooseMove request received!");
        //     System.out.println(params.get("x"));
        //     System.out.println(params.get("y"));

        //     int x = Integer.valueOf(params.get("x"));
        //     int y = Integer.valueOf(params.get("y"));

        //     State state = game.chooseMove(new Pos(x, y));
        //     setWorkerId = game.getChosenWorkerId();

        //     if (state == null) {
        //         return newFixedLengthResponse(new State(game.getCurrState(), State.STERR).toString());
        //     }
        //     return newFixedLengthResponse(state.toString());
        // } else if (uri.equals("/move")) {
        //     System.out.println("App: move request received!");
        //     System.out.println(params.get("x"));
        //     System.out.println(params.get("y"));

        //     int x = Integer.valueOf(params.get("x"));
        //     int y = Integer.valueOf(params.get("y"));
        //     int playerId = game.getNextPlayerId();

        //     State state = game.move(playerId, setWorkerId, new Pos(x, y));
        //     if (state == null) {
        //         return newFixedLengthResponse(new State(game.getCurrState(), State.STERR).toString());
        //     }
        //     return newFixedLengthResponse(state.toString());
        // } else if (uri.equals("/build")) {
        //     System.out.println("App: Build request received!");
        //     System.out.println(params.get("x"));
        //     System.out.println(params.get("y"));

        //     int x = Integer.valueOf(params.get("x"));
        //     int y = Integer.valueOf(params.get("y"));
        //     int playerId = game.getNextPlayerId();

        //     State state = game.build(playerId, setWorkerId, new Pos(x, y));
        //     if (state == null) {
        //         return newFixedLengthResponse(new State(game.getCurrState(), State.STERR).toString());
        //     }
        //     return newFixedLengthResponse(state.toString());
        // } else if (uri.equals("/newgame")) {
        //     this.inGodMode = false;
        //     System.out.println("App: New Game request received!");

        //     this.game = new GameImpl();
        //     setWorkerId = 1;

        //     return null;
        // } else if (uri.equals("/newgodgame")) {
        //     this.inGodMode = true;
        //     System.out.println("App: New God Game request received!");
        //     String god1 = params.get("god1");
        //     String god2 = params.get("god2");

        //     this.game = new GodGame(new GameImpl(), god1, god2);
        //     setWorkerId = 1;

        //     return null;
        // } else if (uri.equals("/preparepower1")) {
        //     System.out.println("App: God Game player 1 use power request received!");
        //     State state = game.setupPower(1, setWorkerId);
        //     return newFixedLengthResponse(state.toString());
        // } else if (uri.equals("/preparepower2")) {
        //     System.out.println("App: God Game player 2 use power request received!");
        //     State state = game.setupPower(2, setWorkerId);
        //     return newFixedLengthResponse(state.toString());
        // } else if (uri.equals("/usepower")) {
        //     System.out.println("App: Use Power Action request received!");
        //     System.out.println(params.get("x"));
        //     System.out.println(params.get("y"));

        //     int x = Integer.valueOf(params.get("x"));
        //     int y = Integer.valueOf(params.get("y"));
        //     int playerId = game.getNextPlayerId();

        //     State state = game.usePower(playerId, setWorkerId, new Pos(x, y));
        //     if (state == null) {
        //     