package com.hdeva.space.base;

public interface ViewHolderBinder<M, VH extends BaseViewHolder> {

    void bind(M model, VH holder);
}
