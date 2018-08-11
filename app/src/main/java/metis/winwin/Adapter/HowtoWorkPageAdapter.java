package metis.winwin.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import metis.winwin.R;

/**
 * Created by Tambora on 27/04/2016.
 */
public class HowtoWorkPageAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
    int[] img;
    String[] text;
    String[] title;
    LayoutInflater inflater;

    public HowtoWorkPageAdapter(Context context, int[] img, String [] title, String[] text) {
        this.context = context;
        this.img = img;
        this.title = title;
        this.text = text;

    }

    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        ImageView imgHeadline;
        TextView txContent, txJudul;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.list_howtowork, container,
                false);

        imgHeadline = (ImageView) itemView.findViewById(R.id.imgSlide);
        txContent = (TextView) itemView.findViewById(R.id.txText);
        txJudul = (TextView) itemView.findViewById(R.id.txTitle);

        txJudul.setText(title[position]);
        txContent.setText(text[position]);

//        imgHeadline.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(img[position]).into(imgHeadline);

        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((LinearLayout) object);


    }
}
