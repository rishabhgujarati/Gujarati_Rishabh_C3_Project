package com.rishabh.entity;
import com.rishabh.exception.itemNotFoundException;
import com.rishabh.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    RestaurantService service = new RestaurantService();
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    private void createMockRestaurant() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        createMockRestaurant();
        LocalTime testTimeforRestauarant_open = LocalTime.parse("11:30:00");
        Restaurant spiedRestaurant = Mockito.spy(restaurant) ;
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(testTimeforRestauarant_open) ;
       // Mockito.doReturn(testTimeforRestauarant_open).when(spiedRestaurant).getCurrentTime();
       // given(spiedRestaurant.getCurrentTime()).willReturn(testTimeforRestauarant_open);
        assertTrue(spiedRestaurant.isRestaurantOpen());

    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        createMockRestaurant();
        LocalTime testTimeforRestauarant_open = LocalTime.parse("22:30:00");
        Restaurant spiedRestaurant = Mockito.spy(restaurant) ;
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(testTimeforRestauarant_open) ;
        assertFalse(spiedRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        createMockRestaurant();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        createMockRestaurant();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        createMockRestaurant();

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("Pakoda"));
    }

 /*   @Test
    void orderTaken() {

    }*/
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//>>>>>>>>>>>>>>>>>>>>>>>>>>>GetCost<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Test
    public void select_item_from_list_should_return_order_cost(){
        int totalCost;
        createMockRestaurant();
        List<String> selectedItemNames = Arrays.asList("Sweet corn soup", "Vegetable lasagne" ) ;
        totalCost = restaurant.getTotalCost(selectedItemNames) ;
        assertEquals(388, totalCost);

    }

    //<<<<<<<<<<<<<<<<<<<<<<<GetCost>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}