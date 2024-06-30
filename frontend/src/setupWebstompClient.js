// Set global to window
window.global = window;

// Import sockjs-client and webstomp-client
import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';

// Export the imported modules
export { SockJS, Stomp };
