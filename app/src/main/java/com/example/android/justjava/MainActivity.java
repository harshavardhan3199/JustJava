/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0, pricePerCup = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameText=(EditText)findViewById( R.id.nameText );
        String nameResult=nameText.getText().toString();
        CheckBox whippedCreamText =(CheckBox)findViewById( R.id.whippedCreamText );
        boolean whippedCreamResult=whippedCreamText.isChecked();
        CheckBox chocolateText=(CheckBox)findViewById( R.id.chocolateText );
        boolean chocolateResult=chocolateText.isChecked();
        int price = calculatePrice( whippedCreamResult,chocolateResult );
        String message = createOrderSummary( price,whippedCreamResult,chocolateResult,nameResult );
        //This is used for sending this data to an gmail app
        Intent intent=new Intent( Intent.ACTION_SENDTO ) ;
        intent.setData( Uri.parse( "mailto:" ) );
        intent.putExtra( Intent.EXTRA_SUBJECT,"Order Summary " );
        intent.putExtra( Intent.EXTRA_TEXT,message );
        if(intent.resolveActivity( getPackageManager())!=null ) {
            startActivity( intent );
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById( R.id.quantity_text_view );
        quantityTextView.setText( "" + number );
    }



    //This is used to increment the quantity
    public void increment(View view) {
        if(quantity>=100)
        {
            quantity=100;
            Toast toast=Toast.makeText( MainActivity.this,"You cannot have more than 100 coffees!",Toast.LENGTH_LONG );
            toast.show();
        }
        else {
            quantity += 1;
            display( quantity );
        }

    }

    //This is used to decrement the quantity
    public void decrement(View view) {
        if (quantity <= 1) {
            quantity = 1;
            Toast toast=Toast.makeText( MainActivity.this,"You Cannot have less than 1 coffee!",Toast.LENGTH_LONG );
            toast.show();
        }

        else {
            quantity -= 1;
        }
        display( quantity );

    }



    // This calculatePrice() will calculate the total price for selected quantity
    private int calculatePrice(boolean whippedCreamResult,boolean chocolateResult) {
        int basePrice=5; // Rate of 1 coffee
        if(whippedCreamResult)
        {
            basePrice+=1;  // If whipped is added we'll add 1 to base price
        }
        if(chocolateResult)
        {
            basePrice+=2; // If chocolate is added we'll add 2 to base price
        }

        return quantity*basePrice;  // return the basePrice with quantity
    }

    /* This createOrderSummary() will print the details whenever the order button is clicked*/
    @SuppressLint("StringFormatInvalid")
    private String createOrderSummary(int price, boolean whippedCreamResult, boolean chocolateResult, String nameResult) {
        String message = getString( R.string.orderSummaryName,nameResult );
        message += "\n " +getString( R.string.orderSummaryWhippedCream,whippedCreamResult );
        message+="\n "+getString( R.string.orderSummaryChocolate,chocolateResult );
        message+="\n "+getString( R.string.orderSummaryQuantity,quantity );
        message += "\n Rs. " +getString( R.string.orderSummaryPrice,NumberFormat.getCurrencyInstance().format( price ));
        message += "\n "+getString( R.string.orderSummaryThanks );
        return message;
    }



}