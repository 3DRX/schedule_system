import { Modal } from 'antd';
import React, { useState } from 'react';

const useFormModal = (modalProps, Slot) => {
    const [visiable, setVisiable] = useState(false);
    const open = () => {
        setVisiable(true);
    };
    const close = () => {
        setVisiable(false);
    };
    const FormModal = (slotProps) => {
        const onCancel = () => {
            close();
        };

        const ref = React.useRef();
        const ok = () => {
            ref.current?.submit()
        };
        return (
            <Modal
                onCancel={onCancel}
                onOk={ok}
                visible={visiable}
                wrapClassName="modal-wrap"
                okText="提交"
                cancelButtonProps={{ shape: 'round' }}
                okButtonProps={{ shape: 'round' }}
                width={600}
                {...modalProps}
            >
                <Slot ref={ref} {...slotProps} afterSubmit={close} />
            </Modal>
        );
    };

    return {
        FormModal,
        open,
    };
};

export default useFormModal;
