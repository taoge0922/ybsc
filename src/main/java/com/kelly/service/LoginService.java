package com.kelly.service;

import com.kelly.model.Tuser;

public interface LoginService {

    Tuser queryByName(String username);
}
