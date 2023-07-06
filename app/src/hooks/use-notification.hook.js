
export function useNotification() {

    async function init() {
        const permission = await Notification.requestPermission()
        
        if(permission!=="granted") {
            throw new Error('Permissão negada')
        }
    }

    function notify({title,body,icon}) {
        new Notification(title, {body, icon})
    }

    async function usarNotificacao({title, body, icon}) {
        try{
            await init()
            notify({title, body, icon})

        } catch(error) {
        }

    }
    return { usarNotificacao }
}