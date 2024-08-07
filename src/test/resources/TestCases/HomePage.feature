#Author: Shankaragouda Patil
Feature: Douglas Feature Testing

  @test
  Scenario Outline: List the products based on filters
    Given Navigate to "https://www.douglas.de/de"
    When Handle the cookie consent
    Then Click on "PARFUM"
    And List the products based on filters
      | Highlights   | Marke   | Produktart   | Geschenk für   | Für Wen   |
      | <Highlights> | <Marke> | <Produktart> | <Geschenk für> | <Für Wen> |
    And Validate the Filter Result

    Examples: 
      | Highlights | Marke   | Produktart      | Geschenk für | Für Wen  |
      | Sale       | Guess   | Eau de Toilette | Geburtstag   | Unisex   |
      | Limitiert  | Versace | Duftset         | Jahrestag    | Männlich |
