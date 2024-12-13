package yk.yfor;

import java.util.function.Consumer;

/**
 * 10.12.2024
 */
public class YForPeek<T> extends YForAbstractPrev<T> {
    private final Consumer<T> consumer;
    private final Runnable onFinish;

    public YForPeek(YFor<T> prev, Consumer<T> consumer, Runnable onFinish) {
        super(prev);
        this.consumer = consumer;
        this.onFinish = onFinish;
    }

    @Override
    public void next(YForResult<T> result) {
        callPrev(result);
        if (result.isPresent()) {
            if (consumer != null) consumer.accept(result.getResult());
        }
        else {
            if (onFinish != null) onFinish.run();
        }
    }
}
