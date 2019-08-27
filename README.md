# aga
Automaton based Graphic Animation

A cross-platform system for teaching finite automata.

It still is using Java Applets and so require old JRE versions.
It should be converted to Web Start for instance.

The animation is defined as a Finite Automata (Mealy Machine), where the output (new animation frame) depends on the current state and the input symbol. Pictures or sounds are described along with graphic transformations (move, zoom, rotate, etc) as part of the state machine definitions, and then, a symbol tape is also defined to allow the state machine to produce the outputs.

An example of a state machine definitions in xml:

```xml
<?xml version="1.0" standalone="no"?>
<AGA VERSION="2.0" WIDTH="200" HEIGHT="150" BACKGROUND="255 255 255" FRAMERATE="10" REPEAT="LOOP">

	<HEAD>
		<TITLE>Apple Worm</TITLE>
		<SUBJECT>Exemple 1</SUBJECT>
	</HEAD>

	<ACTOR ID="WORM" TYPE="GRAPHICS" STATES="3" SYMBOLS="1">
		<OUTPUT ID="1" SOURCE="walk.gif" X="0" Y="13"/>
		<OUTPUT ID="2" SOURCE="eat.gif" X="0" Y="42"/>
  	<TRANSF>
			<FROM STATE="1">
				<TO STATE="2" SYMBOL="1" OUTPUT="1"/>
			</FROM>
			<FROM STATE="2">	
				<TO STATE="3" SYMBOL="1" OUTPUT="2"/>
			</FROM>
		</TRANSF>
	</ACTOR>

	<ACTOR ID="MACA" TYPE="GRAPHICS" STATES="3" SYMBOLS="1">
		<OUTPUT ID="1" SOURCE="apple.gif" X="0" Y="0"/>
		<OUTPUT ID="2" SOURCE="byte.gif" X="0" Y="0"/>		
		<TRANSF>
			<FROM STATE="1">
				<TO STATE="2" SYMBOL="1" OUTPUT="1"/>				
			</FROM>
			<FROM STATE="2">
				<TO STATE="3" SYMBOL="1" OUTPUT="2"/>	
			</FROM>
		</TRANSF>	
	</ACTOR>

	<TAPE ID="eat">
	  <CEL SYMBOL="1" TIME="200" FN="TRANSLATE 2 50 N"/>
	  <CEL SYMBOL="1" TIME="200" FN="TRANSLATE 30 0 Y"/>		
  </TAPE>

	<TAPE ID="byte">
	  <CEL SYMBOL="1" TIME="400" FN="TRANSLATE 142 70 N"/>
	  <CEL SYMBOL="1" TIME="600"/>
  </TAPE>

	<INSTANCE ID="hungry worm" ACTOR="worm" ORDER="1">
		<USE TAPE="eat"/>
		<USE TAPE="happy"/>
	</INSTANCE>	

	<INSTANCE ID="apple" ACTOR="apple" ORDER="2">
		<USE TAPE="byte"/>
	</INSTANCE>
</AGA>	
```
