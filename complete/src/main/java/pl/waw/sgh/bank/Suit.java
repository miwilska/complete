/*
 *                     GNU GENERAL PUBLIC LICENSE
 *                        Version 3, 29 June 2007
 *
 *     "Pasjanse" - solitaire - card games for one person
 *     Copyright (C) {2016}  {Magdalena Wilska}
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *
 */

package pl.waw.sgh.bank;

import java.awt.*;

/**
 *  Enum type to define Card suits type with color (black and red) as a private final field
 *  @author Fred Swartz - December 2004 - Placed in the public domain.
 *  @author magdalena - on 30.09.16 a few changes
 */
/////////////////////////////////////////////////////////////////////////////
public enum Suit {
    //================================================================ constants
    SPADES(Color.BLACK), HEARTS(Color.RED), CLUBS(Color.BLACK), DIAMONDS(Color.RED);
    
    //==================================================================== field
    private final Color _color;
    
    //============================================================== constructor
    Suit(Color color) {
        _color = color;
    }
    
    //================================================================= getColor
    public Color getColor() {
        return _color;
    }

}