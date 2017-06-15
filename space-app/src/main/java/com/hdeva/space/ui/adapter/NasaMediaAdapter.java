package com.hdeva.space.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.hdeva.space.R;
import com.hdeva.space.base.BaseViewHolder;
import com.hdeva.space.core.model.images.ImageSearchResponse;
import com.hdeva.space.core.model.images.MediaType;
import com.hdeva.space.core.model.images.NasaItem;
import com.hdeva.space.ui.adapter.binder.NasaAudioBinder;
import com.hdeva.space.ui.adapter.binder.NasaImageBinder;
import com.hdeva.space.ui.adapter.binder.NasaVideoBinder;
import com.hdeva.space.ui.adapter.holder.LargeNativeAdViewHolder;
import com.hdeva.space.ui.adapter.holder.NasaAudioViewHolder;
import com.hdeva.space.ui.adapter.holder.NasaImageViewHolder;
import com.hdeva.space.ui.adapter.holder.NasaVideoViewHolder;
import com.hdeva.space.ui.helper.AdHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NasaMediaAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private NasaImageBinder imageBinder;
    private NasaVideoBinder videoBinder;
    private NasaAudioBinder audioBinder;
    private AdHelper adHelper;

    private List<NasaMediaListItem> items;

    @Inject
    public NasaMediaAdapter(NasaImageBinder imageBinder, NasaVideoBinder videoBinder, NasaAudioBinder audioBinder, AdHelper adHelper) {
        this.imageBinder = imageBinder;
        this.videoBinder = videoBinder;
        this.audioBinder = audioBinder;
        this.adHelper = adHelper;
        setHasStableIds(true);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder;
        switch (viewType) {
            case R.layout.item_nasa_image:
                holder = new NasaImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nasa_image, parent, false));
                break;
            case R.layout.item_nasa_video:
                holder = new NasaVideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nasa_video, parent, false));
                break;
            case R.layout.item_nasa_audio:
                holder = new NasaAudioViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nasa_audio, parent, false));
                break;
            case R.layout.item_large_native_ad:
                holder = new LargeNativeAdViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_large_native_ad, parent, false));
                break;
            default:
                throw new IllegalStateException("Invalid view type requested: " + viewType);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case R.layout.item_nasa_image:
                imageBinder.bind(items.get(position).item, (NasaImageViewHolder) holder);
                break;
            case R.layout.item_nasa_video:
                videoBinder.bind(items.get(position).item, (NasaVideoViewHolder) holder);
                break;
            case R.layout.item_nasa_audio:
                audioBinder.bind(items.get(position).item, (NasaAudioViewHolder) holder);
                break;
            case R.layout.item_large_native_ad:
                adHelper.bind((LargeNativeAdViewHolder) holder);
                break;
            default:
                throw new IllegalStateException("Invalid view type requested: " + holder.getItemViewType());
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public long getItemId(int position) {
        int hash = 0;
        if (items != null) {
            NasaMediaListItem listItem = items.get(position);
            if (listItem.isAd()) {
                hash = listItem.hashCode();
            } else {
                NasaItem item = listItem.item;
                hash = Stream.of(item.data)
                        .map(data -> data.nasaId)
                        .collect(Collectors.joining())
                        .hashCode();
            }
        }
        return hash;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType;
        if (items == null) {
            viewType = 0;
        } else if (items.get(position).isAd()) {
            viewType = R.layout.item_large_native_ad;
        } else {
            @MediaType
            String mediaType = items.get(position).getMediaType();
            switch (mediaType) {
                case MediaType.IMAGE:
                    viewType = R.layout.item_nasa_image;
                    break;
                case MediaType.VIDEO:
                    viewType = R.layout.item_nasa_video;
                    break;
                case MediaType.AUDIO:
                    viewType = R.layout.item_nasa_audio;
                    break;
                default:
                    viewType = 0;
                    break;
            }
        }
        return viewType;
    }

    public void setItems(ImageSearchResponse response) {
        if (response != null) {
            items = mapItems(response.items);
        }
        notifyDataSetChanged();
    }

    private List<NasaMediaListItem> mapItems(List<NasaItem> items) {
        List<NasaMediaListItem> list = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            list.add(NasaMediaListItem.forItem(items.get(i)));
            if (adHelper.shouldShowAds() && adHelper.frequency(i))
                list.add(NasaMediaListItem.forAd());
        }
        return list;
    }

    static class NasaMediaListItem {

        NasaItem item;

        static NasaMediaListItem forItem(NasaItem item) {
            NasaMediaListItem result = new NasaMediaListItem();
            result.item = item;
            return result;
        }

        static NasaMediaListItem forAd() {
            return new NasaMediaListItem();
        }

        boolean isAd() {
            return item == null;
        }

        @MediaType
        public String getMediaType() {
            return item == null ? null : item.data.get(0).mediaType;
        }
    }
}
