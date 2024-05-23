import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class WeatherForecast extends JFrame {
    private JTextArea forecastData;
    private JPanel panelMain;
    private JTextField cityName;
    private JButton forecastButton;
    private JButton convertUnitsBtn;
    private JButton convertBackBtn;


    public WeatherForecast() {
        forecastData = new JTextArea(15, 40); // Set preferred size
        forecastData.setEditable(false); // Make the JTextArea read-only

        // Wrap the JTextArea in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(forecastData);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        cityName = new JTextField(20);
        forecastButton = new JButton("Get Forecast");
        convertUnitsBtn = new JButton("Convert Units");
        convertBackBtn = new JButton("Convert Back");

        // Set up the main panel with GridBagLayout
        panelMain = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Set padding

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4; // Span across 4 columns
        panelMain.add(cityName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Reset to single column span
        panelMain.add(forecastButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panelMain.add(convertUnitsBtn, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        panelMain.add(convertBackBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4; // Span across 4 columns
        gbc.fill = GridBagConstraints.BOTH; // Make the scrollPane fill the space
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panelMain.add(scrollPane, gbc);
        //TODO: get buttons involved to have actual UI functionality
        forecastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ApiHandler API = new ApiHandler();
                API.API_Forecast_Call(cityName.getText());
                String outputString = API.getForecastEntry();
                forecastData.setText(outputString);

            }
        });
        convertUnitsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ApiHandler API = new ApiHandler();
                API.API_Forecast_Call(cityName.getText());
                String outputString = API.getForecastEntryConverted();
                forecastData.setText(outputString);
            }
        });
        convertBackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ApiHandler API = new ApiHandler();
                API.API_Forecast_Call(cityName.getText());
                String outputString = API.getForecastEntry();
                forecastData.setText(outputString);
            }
        });

        setContentPane(panelMain);
        setTitle("Weather Forecast");
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WeatherForecast::new);
    }
}

