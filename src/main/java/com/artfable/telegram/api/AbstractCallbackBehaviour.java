package com.artfable.telegram.api;

import com.artfable.telegram.api.keyboard.InlineKeyboardBtn;

import com.artfable.telegram.api.keyboard.InlineKeyboardBtn;

/**
 * Base class for all {@link CallbackBehaviour}s. Describe callback for the button with the specific {@link #key}.
 * Mapping is done only by {@link #key}, all values will be routed for the same {@link CallbackBehaviour} if they have a same key.
 * Format: key(-value)?
 * Key can't contain '-' symbol.
 *
 * @author aveselov
 * @since 13/08/2020
 */
public abstract class AbstractCallbackBehaviour implements CallbackBehaviour {

    private String key;

    protected AbstractCallbackBehaviour(String key) {
        if (key.contains("-")) {
            throw new IllegalArgumentException("Key can't contain '-' symbol");
        }
        this.key = key;
    }

    @Override
    public boolean parse(Update update) {
        if (update.getCallbackQuery() == null || update.getCallbackQuery().getData() == null || !key.equals(getKey(update.getCallbackQuery()))) {
            return false;
        }

        parse(getValue(update.getCallbackQuery()), update.getCallbackQuery());
        return true;
    }

    protected abstract void parse(String value, CallbackQuery callbackQuery);

    @Override
    public InlineKeyboardBtn createBtn(String text, String value) {
        return new InlineKeyboardBtn(text, key + "-" + value);
    }

    private String getKey(CallbackQuery callbackQuery) {
        return callbackQuery.getData() != null ? callbackQuery.getData().replaceAll("-.*", "") : null;
    }

    private String getValue(CallbackQuery callbackQuery) {
        return callbackQuery.getData() != null ? callbackQuery.getData().replaceFirst(key + "-?", "") : null;
    }
}
