package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page.
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2324s2-cs2103t-w09-3.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "For more details, refer to the user guide: " + USERGUIDE_URL
            + "\n\nThings to note:"
            + "\n  • Items in square brackets are optional."
            + "\n  • Items with '...' after them can be used multiple times including zero times."
            + "\n  • Click the 'Command' header to sort the commands alphabetically."
            + "\n  • Press 'q' to close this window.";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private TableView<CommandSummary> userGuideTable;

    @FXML
    private TableColumn<CommandSummary, String> commandColumn;

    @FXML
    private TableColumn<CommandSummary, String> usageColumn;

    private final ObservableList<CommandSummary> guideItems = FXCollections.observableArrayList();


    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        commandColumn.setCellValueFactory(new PropertyValueFactory<>("command"));
        usageColumn.setCellValueFactory(new PropertyValueFactory<>("usage"));
        fillCommandSummaryTable();
        userGuideTable.setItems(guideItems);

        Scene scene = getRoot().getScene();
        if (scene != null) {
            scene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
        }
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }


    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }

    /**
     * Fills the command summary table with all available commands.
     */
    private void fillCommandSummaryTable() {
        guideItems.add(new CommandSummary("help", "Open command summary "
                + "(can also be accessed by pressing F1 key)"));
        fillMainCommandSummary();
        fillArchiveRelatedCommandSummary();
        fillReservationRelatedCommandSummary();
        guideItems.add(new CommandSummary("clear", "Remove all persons and reservations "
                + "from CulinaryContacts.\n"
                + "A confirmation message will be shown, type y to proceed with clearing "
                + "or otherwise to cancel clearing."));
        guideItems.add(new CommandSummary("exit", "Close the address book."));
    }

    /**
     * Fills the command summary table with main commands.
     * These include 'add', 'list', 'edit', 'find', 'filter', 'delete' commands.
     */
    private void fillMainCommandSummary() {
        guideItems.add(new CommandSummary("add", "Add a new contact.\n"
                + "add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…\n"
                + "e.g., add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/supplier "
                + "t/seafood"));
        guideItems.add(new CommandSummary("list", "List all contacts.\n"));
        guideItems.add(new CommandSummary("edit", "Edit a contact.\n"
                + "edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…\n"
                + "e.g., edit 1 p/91234567 e/johndoe@example.com"));
        guideItems.add(new CommandSummary("find", "Find contacts whose names contain "
                + "any of the given keywords.\n"
                + "find KEYWORD [MORE_KEYWORDS]...\n"
                + "e.g., find John"));
        guideItems.add(new CommandSummary("filter", "Filter contacts with specified tags.\n"
                + "filter TAG [MORE_TAGS]...\n"
                + "e.g., filter supplier seafood"));
        guideItems.add(new CommandSummary("delete", "Delete a contact.\n"
                + "delete INDEX\n"
                + "e.g., delete 1"));
    }

    /**
     * Fills the command summary table with archive-related commands.
     * These include 'archive', 'unarchive', 'alist'.
     */
    private void fillArchiveRelatedCommandSummary() {
        guideItems.add(new CommandSummary("archive", "Archive a contact.\n"
                + "archive INDEX\n"
                + "e.g., archive 1"));
        guideItems.add(new CommandSummary("unarchive", "Unarchive a contact.\n"
                + "unarchive INDEX\n"
                + "e.g., unarchive 1"));
        guideItems.add(new CommandSummary("alist", "View the archived list.\n"));
    }

    /**
     * Fills the command summary table with reservation-related commands.
     * These include 'rsv', 'rsvdel', 'rsvlist', 'rsvsort'.
     */
    public void fillReservationRelatedCommandSummary() {
        guideItems.add(new CommandSummary("rsv", "Add a reservation for the person "
                + "at the specified index"
                + "rsv INDEX d/DATE t/TIME p/PAX\n"
                + "e.g., rsv 1 d/2024-04-17 t/1800 p/8"));
        guideItems.add(new CommandSummary("rsvdel", "Delete a reservation.\n"
                + "rsvdel INDEX\n"
                + "e.g., rsvdel 1"));
        guideItems.add(new CommandSummary("rsvsort", "Sort the reservation list.\n"));
    }

    /**
     * Handles the key pressed event to close the help window when "q" is pressed.
     * @param event The key event.
     */
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.Q) {
            hide();
            event.consume();
        }
    }
}
