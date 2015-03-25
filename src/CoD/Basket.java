/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoD;
import CoD.menu.Product;

/**
 *
 * @author Stefan
 */
public class Basket
{
    private final   java.util.HashMap<Product, Integer> basket;
    private int     total;
    
    /**
     * Makes an empty basket which can store products and their quantities.
     */
    public Basket()
    {
        basket = new java.util.HashMap();
        total = 0;
    }
    
    /**
     * Adds a Product p to the basket with the default quantity of 1.
     * 
     * @param p Product object to add
     */
    public void add(Product p)
    {
        basket.put(p, 1);
        total += p.getPrice();
    }
    
    /**
     * Increments the quantity of Product p by 1.
     * 
     * @param p
     */
    public void increment(Product p)
    {
        basket.computeIfPresent(p, (k, v) -> v + 1);
        total += p.getPrice();
    }
    
    /**
     * Decrements the quantity of Product p by 1.
     * 
     * @param p
     */
    public void decrement(Product p)
    {
        basket.computeIfPresent(p, (k, v) -> v - 1);
        total -= p.getPrice();
    }
    
    public boolean setQuantity(Product p, int q)
    {
        boolean ok = false;
        if (basket.containsKey(p))
        {
            if (q > 0)
            {
                int cq = basket.get(p);
                if (q < cq) 
                    total -= p.getPrice() * (cq - q);
                else if (q > cq) total += p.getPrice() * (q - cq);
                basket.compute(p, (k, v) -> v = q);
            }
            else  if (q == 0) remove(p);
            ok = true;
        }
        return ok;
    }
    
    /**
     * Removes a Product p from the basket.
     * 
     * @param p
     */
    public void remove(Product p)
    {
        basket.remove(p);
        recalculateTotal();
    }
    
    /**
     *
     */
    public void recalculateTotal()
    {
        int t = 0;
        for(Product p : basket.keySet())
        {
            t = p.getPrice() * basket.get(p);
        }
        total = t;
    }
    
    public int getTotal() { return total; }
}
