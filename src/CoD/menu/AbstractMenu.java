/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoD.menu;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Stefan
 */
public abstract class AbstractMenu
{

    private final java.util.LinkedList<Product> menuList;
    private final int                           size;

    /**
     *
     * @param menuSize maximum number of products the menu should contain
     */
    protected AbstractMenu(int menuSize)
    {
        menuList = new java.util.LinkedList();
        size = menuSize;
    }
    
//    public static AbstractMenu buildMenu(CoD.DBConnect conn, String type)
//    {
//        switch (type)
//        {
//            case "breakfast" : return buildBreakfastMenu(conn);
//            default : 
//            {
//                System.err.println("Error in buildMenu: no such menu type.");
//                return null;
//            }
//        }
//    }
    
    /**
     *
     * @param conn connection to database
     * @param type breakfast, lunch, dinner or drink
     * @return
     */
        
    public static AbstractMenu buildMenu(CoD.DBConnect conn, String type)
    {
        AbstractMenu menu;
        switch (type)
        {
            case "breakfast" :
            {
                menu = new BreakfastMenu();
                break;
            }
            case "lunch" :
            {
                menu = new LunchMenu();
                break;
            }
            case "dinner" :
            {
                menu = new DinnerMenu();
                break;
            }
            case "drink" :
            {
                menu = new DrinksMenu();
                break;
            }
            default :
            {
                System.err.println("Error in buildMenu: no such menu type: " + type);
                return null;
            }
        }
        String food_menus = "SELECT * FROM Products WHERE isAvailable=true "
                    + "AND (product_type = '" + type + "' OR product_type = '" + type + "_drink')";
        String drink_menu = "SELECT * FROM Products WHERE isAvailable=true "
                    + "AND product_type = '" + type + "_drink'";
        try
        {
            ResultSet rs;
            rs = conn.read(!type.equals("drink") ? food_menus : drink_menu);
            while(rs.next())
            {
                menu.addProduct(new Product(
                        rs.getString("product_name"),
                        rs.getString("product_description"),
                        rs.getInt("price"),
                        rs.getString("product_type"),
                        rs.getInt("intendedDuration")));
            }
        } catch (SQLException ex) 
        {
            System.err.println("SQL Error in buildBreakfastMenu: " + ex.getMessage());
        }
        return menu;
    }
    
    /**
     *
     * @param p Product object to add to menu
     * @return true if adding succeeded (i.e. menu is not full), false otherwise
     */
    public boolean addProduct(Product p)
    {
        if (menuList.size() != size)
            return menuList.add(p);
        else return false;
    }
    
    /**
     *
     * @param i index of product
     * @return Product object at index i
     * @throws IndexOutOfBoundsException
     */
    public Product getProduct(int i) throws IndexOutOfBoundsException
    {
        return menuList.get(i);
    }
    
    /**
     *
     * @param productName name of product to remove
     * @return true if product is in menu, false otherwise
     */
    public boolean removeProduct(String productName)
    {
        boolean found = false;
        for(java.util.Iterator<Product> i = menuList.iterator(); i.hasNext(); )
        {
            Product p = i.next();
            if (p.getName().equals(productName))
            {
                i.remove();
                found = true;
                break;
            }
        }
        return found;
    }
    
    /**
     *
     * @return size of the menu list
     */
    public int getSize() { return size; }
}
