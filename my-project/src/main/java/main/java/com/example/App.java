package main.java.com.example;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.SimpleWebServer;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class App extends SimpleWebServer {

    /**
     * The main method to start the server application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    private Game game;

    /**
     * Start the server at :8080 port.
     */
    public App() throws IOException {
        super(null, 8080, new File("/var/www/html"), true);
        this.game = new Game(); // Initialize the game
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning!\n");
    }

    /**
     * Handles HTTP requests and returns responses.
     * This method processes various game actions based on the request URI and parameters received in the HTTP session.
     * Supported actions include initializing a new game, placing workers, moving workers, building structures, setting god cards, and passing actions.
     * After processing an action, the method updates the game state and returns it in JSON format.
     *
     * @param session The HTTP session containing the request data.
     * @return A fixed length response with the updated game state in JSON format.
     */
    @Override
    public Response serve(IHTTPSession session) {
        // Extract the URI and parameters from the HTTP session
        String uri = session.getUri();
        @SuppressWarnings("deprecation")
        Map<String, String> params = session.getParms();
        GameState gameState = new GameState();

        // Process the request based on the URI

        if (uri.startsWith("/api/")) {
            System.out.println(uri);
            switch (uri) {
                // Initialize a new game
                case "/api/newGame":
                    this.game = new Game();
                    break;
                case "/api/initialize":
                    // Initialize workers on the board
                    this.game = this.game.initializeWorker(
                            Integer.parseInt(params.get("x1")), Integer.parseInt(params.get("y1")),
                            Integer.parseInt(params.get("x2")), Integer.parseInt(params.get("y2")));
                    break;
                // Move a worker on the board
                case "/api/move":
                    this.game = this.game.move(
                            Integer.parseInt(params.get("x1")), Integer.parseInt(params.get("y1")),
                            Integer.parseInt(params.get("x2")), Integer.parseInt(params.get("y2")));
                    break;
                // Build a tower or dome on the board
                case "/api/build":
                    this.game = this.game.build(
                            Integer.parseInt(params.get("x1")), Integer.parseInt(params.get("y1")));
                    break;
                // Pass the current action
                case "/api/passAction":
                    this.game = this.game.passAction();
                    break;
                default:
                    return super.serve(session);
            }

            gameState.updateFromGame(this.game);
            System.out.println(gameState.toJson());
            Response response = newFixedLengthResponse(Response.Status.OK, "application/json", gameState.toJson());
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
            return response;
        }

        return super.serve(session);
    }

}
