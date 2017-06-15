package com.hdeva.space.base;

interface Presenter<H> {
    void attach(H host);

    void detach();

    boolean isAttached();
}
