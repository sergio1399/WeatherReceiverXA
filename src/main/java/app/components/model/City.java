package app.components.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Класс-модель города.
 * @author Сергей Солдатов
 * @version 1.0
 */
@Entity(name = "City")
public class City {

    /**
     * Id города
     */
    @Id
    private Integer id;

    /**
     * Название города
     */
    private String name;

    /**
     * Регион
     */
    private String region;

    /**
     * Страна
     */
    private String country;

    /**
     * Список прогнозов для города
     */
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Forecast> forecasts;

    /**
     * Служебное поле Hibernate
     */
    @Version
    private Integer version;

    /**
     * Процедура добавления прогноза для города
     * @param forecast - прогноз погоды
     */
    public void addForecast(Forecast forecast)
    {
        getForecasts().add(forecast);
        forecast.setCity(this);
    }

    /**
     * Процедура удаления прогноза
     * @param forecast - прогноз погоды
     */
    public void removeForecast(Forecast forecast)
    {
        getForecasts().remove(forecast);
        forecast.setCity(null);
    }


    /**
     * @return {@link #forecasts}
     */
    public Set<Forecast> getForecasts() {
        return forecasts;
    }

    /**
     * @param forecasts {@link #forecasts}
     */
    public void setForecasts(Set<Forecast> forecasts) {
        this.forecasts = forecasts;
    }

    /**
     * @return {@link #id}
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id {@link #id}
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * @param name {@link #name}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return {@link #region}
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region {@link #region}
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * @return {@link #country}
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country {@link #country}
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Конструктор по умолчанию
     */
    public City() {
    }

    /**
     * Конструктор с параметрами
     * @param id - идентификатор
     * @param name - цена
     * @param region - регион
     * @param country - страна
     */
    public City(Integer id, String name, String region, String country) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.country = country;
    }

    /**
     * @return строковое представление объекта города
     */
    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

