// **********************************************************************
//
// Copyright (c) 2003-2016 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.6.2
//
// <auto-generated>
//
// Generated from file `Ed.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package RpcEd;

public final class DecryptResponseHolder extends Ice.ObjectHolderBase<DecryptResponse>
{
    public
    DecryptResponseHolder()
    {
    }

    public
    DecryptResponseHolder(DecryptResponse value)
    {
        this.value = value;
    }

    public void
    patch(Ice.Object v)
    {
        if(v == null || v instanceof DecryptResponse)
        {
            value = (DecryptResponse)v;
        }
        else
        {
            IceInternal.Ex.throwUOE(type(), v);
        }
    }

    public String
    type()
    {
        return DecryptResponse.ice_staticId();
    }
}