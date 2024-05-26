import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;



public class ApiHandler {
    String weatherEntry, weatherEntryConverted, forecastEntry, forecastEntryConverted;

    void API_Forecast_Call(String cityName){
        OkHttpClient client = new OkHttpClient();

        String apiKey = "3cb7311ba5dd57a76cbbd465a6217e38";

        //Current weather call
        String urlC = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + apiKey;
        //Forcast weather call
        String urlF = "https://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&appid=" + apiKey;


        Request request1 = new Request.Builder()
                .url(urlC)
                .build();

        Request request2 = new Request.Builder()
                .url(urlF)
                .build();
        try {
            Response response = client.newCall(request1).execute();
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                ParseWeatherData(responseData);
            } else {
                System.out.println("Error: " + response.code() + " " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Response response = client.newCall(request2).execute();
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                ParseForecastData(responseData);
            } else {
                System.out.println("Error: " + response.code() + " " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void ParseWeatherData(String jsonData) {
        JSONObject jsonObject = new JSONObject(jsonData);
        //TODO: Figure out how to display icons from weather data in GUI using API

        StringBuilder forecastBuilder = new StringBuilder();
        StringBuilder forecastBuilderConverted = new StringBuilder();


        JSONObject currWeather = new JSONObject(jsonData);

        double tempK = currWeather.getJSONObject("main").getDouble("temp");
        double temp_MaxK = currWeather.getJSONObject("main").getDouble("temp_max");
        double temp_MinK = currWeather.getJSONObject("main").getDouble("temp_min");
        double windSpeedM = currWeather.getJSONObject("wind").getDouble("speed");
        JSONObject weatherObject = currWeather.getJSONArray("weather").getJSONObject(0);
        String weatherEffect = weatherObject.getString("main");
        int humidity = currWeather.getJSONObject("main").getInt("humidity");

        double tempF = 1.8 * (tempK - 273.0) + 32.0;
        double temp_MaxF = 1.8 * (temp_MaxK - 273.0) + 32.0;
        double temp_MinF = 1.8 * (temp_MinK - 273.0) + 32.0;
        double tempC = convertTempToC(tempF);
        double temp_MaxC = convertTempToC(temp_MaxF);
        double temp_MinC = convertTempToC(temp_MinF);
        double windSpeedF = convertWindSpeedToFPerSec(windSpeedM);
        String entry = String.format(" Current Weather\n Temperature: %.2f°F\n" +
                        " MinTemp: %.2f°F\n MaxTemp: %.2f°F\n Wind Speed: %.2fm/s\n Weather: %s\n Humidity: %d\n\n",
                tempF, temp_MinF, temp_MaxF, windSpeedM, weatherEffect, humidity);
        String entryConverted = String.format("Current Weather\n Temperature: %.2f°C\n" +
                        " MinTemp: %.2f°C\n MaxTemp: %.2f°C\n Wind Speed: %.2ff/s\n Weather: %s\n Humidity: %d\n\n",
                tempC, temp_MinC, temp_MaxC, windSpeedF, weatherEffect, humidity);

        forecastBuilder.append(entry);
        forecastBuilderConverted.append(entryConverted);

        weatherEntry = forecastBuilder.toString();
        weatherEntryConverted = forecastBuilderConverted.toString();
    }

    void ParseForecastData(String jsonData) {
        JSONObject jsonObject = new JSONObject(jsonData);
        //TODO: Figure out how to display icons from weather data in GUI using API

        StringBuilder forecastBuilder = new StringBuilder();
        StringBuilder forecastBuilderConverted = new StringBuilder();

        JSONArray forecastList = jsonObject.getJSONArray("list");
        for (int i = 0; i < forecastList.length(); i++) { //TODO: DO loop calculation to get 5 day forecast
            JSONObject forecast = forecastList.getJSONObject(i);
            String timeStamp = forecast.getString("dt_txt");
            JSONObject main = forecast.getJSONObject("main");
            JSONObject wind = forecast.getJSONObject("wind");
            JSONArray weatherArray = forecast.getJSONArray("weather");
            JSONObject weather = weatherArray.getJSONObject(0);
            String weatherEffect = weather.getString("main");
            double tempK = main.getDouble("temp");
            double windSpeedM = wind.getDouble("speed");
            int humidity = main.getInt("humidity");
            double temp_MaxK = main.getDouble("temp_max");
            double temp_MinK = main.getDouble("temp_min");
            double tempF = 1.8 * (tempK - 273.0) + 32.0;
            double temp_MaxF = 1.8 * (temp_MaxK - 273.0) + 32.0;
            double temp_MinF = 1.8 * (temp_MinK - 273.0) + 32.0;
            double tempC = convertTempToC(tempF);
            double temp_MaxC = convertTempToC(temp_MaxF);
            double temp_MinC = convertTempToC(temp_MinF);
            double windSpeedF = convertWindSpeedToFPerSec(windSpeedM);
            String entry = String.format(" Timestamp: %s\n Temperature: %.2f°F\n" +
                            " MinTemp: %.2f°F\n MaxTemp: %.2f°F\n Wind Speed: %.2fm/s\n Weather: %s\n Humidity: %d\n\n",
                    timeStamp, tempF, temp_MinF, temp_MaxF, windSpeedM, weatherEffect, humidity);
            String entryConverted = String.format("Timestamp: %s\n Temperature: %.2f°C\n" +
                            " MinTemp: %.2f°C\n MaxTemp: %.2f°C\n Wind Speed: %.2ff/s\n Weather: %s\n Humidity: %d\n\n",
                    timeStamp, tempC, temp_MinC, temp_MaxC, windSpeedF, weatherEffect, humidity);

            forecastBuilder.append(entry);
            forecastBuilderConverted.append(entryConverted);
        }
        forecastEntry = forecastBuilder.toString();
        forecastEntryConverted = forecastBuilderConverted.toString();
}
    double convertTempToC(double temp) {
        temp = (temp - 32.0) * (5.0 / 9.0);
        return temp;
    }
    double convertWindSpeedToFPerSec(double speed) {
        speed *= 3.281;
        return speed;
    }

    String getForecastEntry(){
        return forecastEntry;
    }
    String getForecastEntryConverted() {
        return forecastEntryConverted;
    }
    String getWeatherEntry(){
        return weatherEntry;
    }
    String getWeatherEntryConverted() {
        return weatherEntryConverted;
    }
}
