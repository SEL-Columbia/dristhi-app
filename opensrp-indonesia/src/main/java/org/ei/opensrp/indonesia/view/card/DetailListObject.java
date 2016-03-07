package org.ei.opensrp.indonesia.view.card;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.prototypes.CardWithList;

/**
 * Created by Dimas Ciputra on 9/21/15.
 */
public class DetailListObject implements CardWithList.ListObject {
    protected Card mParentCard;
    protected CardWithList.OnItemClickListener mOnItemClickListener;
    protected boolean mItemSwipeable = false;
    protected CardWithList.OnItemSwipeListener mOnItemSwipeListener;

    public String subject;
    public String value;
    public String clickableText;
    protected String mObjectId;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getClickableText() {
        return clickableText;
    }

    public void setClickableText(String clickableText) {
        this.clickableText = clickableText;
    }

    public DetailListObject(Card parentCard) {
        this.mParentCard = parentCard;
    }

    public String getObjectId() {
        return this.mObjectId;
    }

    public Card getParentCard() {
        return null;
    }

    public void setObjectId(String objectId) {
        this.mObjectId = objectId;
    }

    public void setOnItemClickListener(CardWithList.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public CardWithList.OnItemClickListener getOnItemClickListener() {
        return this.mOnItemClickListener;
    }

    public boolean isSwipeable() {
        return this.mItemSwipeable;
    }

    public void setSwipeable(boolean isSwipeable) {
        this.mItemSwipeable = isSwipeable;
    }

    public CardWithList.OnItemSwipeListener getOnItemSwipeListener() {
        return this.mOnItemSwipeListener;
    }

    public void setOnItemSwipeListener(CardWithList.OnItemSwipeListener onItemSwipeListener) {
        if(onItemSwipeListener != null) {
            this.mItemSwipeable = true;
        } else {
            this.mItemSwipeable = false;
        }

        this.mOnItemSwipeListener = onItemSwipeListener;
    }
}
