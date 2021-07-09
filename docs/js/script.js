function prn(s){
	console.log(s);
	let ta = document.querySelector('#ta');
	ta.value = ta.value + "\n" + s;
}
function prnCoord(x, y, s){ prn(`(${x}, ${y}) ${s}`); }

var state = {dragging: false};

function setup(){
	let ta = document.createElement('textarea');
	ta.setAttribute('id', 'ta');
	document.querySelector('.jsTimeline').appendChild(ta);
 
	let panel = document.querySelector('.control-panel');
	panel.addEventListener('mousedown', ev=>{
		prnCoord(ev.pageX, ev.pageY, 'down');
		state.dragging = true;
	});
	panel.addEventListener('mousemove', ev=>{
		if ( !state.dragging ) return;
		prnCoord(ev.pageX, ev.pageY, 'move');
	});
	panel.addEventListener('mouseup', ev=>{
		if ( !state.dragging ) return;
		state.dragging = false;
		prnCoord(ev.pageX, ev.pageY, 'up');
	});
	panel.addEventListener('mouseleave', ev=>{
		if ( !state.dragging ) return;
		state.dragging = false;
		prnCoord(ev.pageX, ev.pageY, 'leave');
	});
	panel.addEventListener('touchstart', ev=>{
		ev.preventDefault();
		prnCoord(ev.changedTouches[0].pageX, ev.changedTouches[0].pageY, 'start');
		state.dragging = true;
	});
	panel.addEventListener('touchmove', ev=>{
		ev.preventDefault();
		if ( !state.dragging ) return;
		prnCoord(ev.changedTouches[0].pageX, ev.changedTouches[0].pageY, 'tmove');
	});
	panel.addEventListener('touchend', ev=>{
		ev.preventDefault();
		if ( !state.dragging ) return;
		state.dragging = false;
		prnCoord(ev.changedTouches[0].pageX, ev.changedTouches[0].pageY, 'leave');
	});
};

setTimeout(setup, 1000);
