//contact-page screen
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

void main(){
  runApp(MyApp());
}

class contact extends StatefulWidget{
    //build method to see changes being made to the UI
    @override
       Widget build(BuildContext context) {
        return MaterialApp(
            title: '',
            theme: ThemeData(),
            home: Scaffold(
                title: Text('Close Contacts',
                textAlign: TextAlign.center,
                fontWeight: FontWeight.bold,),
                body:IconButton(
                icon: Icon(Icons.)),
            ), //end of Scaffold
           ); //end of MaterialApp widget
       }



}

