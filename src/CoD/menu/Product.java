/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoD.menu;

/**
 *
 * @author Stefan
 */
public class Product
{
    private String          name;
    private String          description;
    private int             price;
    private final String    type;
    private int             intendedDuration;
    
    /**
     *
     * @param name
     * @param price
     * @param type
     */
    
    public Product(String name, int price, String type)
    {
        this.name = name;
        description = "";
        this.price = price;
        this.type = type;
    }
    
    /**
     *
     * @param name
     * @param description
     * @param price
     * @param type
     * @param intendedDuration
     */
    
    public Product(
            String name,
            String description,
            int price,
            String type,
            int intendedDuration)
    {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.intendedDuration = intendedDuration;
    }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public int getPrice() { return price; }
    
    public String getType() { return type; }
    
    public int getDuration() {return intendedDuration; }

    public void setName(String name) { this.name = name; }
    
    public void setDescription(String desc) { description = desc; }
    
    public void setPrice(int price) { this.price = price; }
}
