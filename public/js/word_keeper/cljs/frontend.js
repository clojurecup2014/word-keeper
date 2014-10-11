// Compiled by ClojureScript 0.0-2342
goog.provide('word_keeper.cljs.frontend');
goog.require('cljs.core');
goog.require('om.dom');
goog.require('om.dom');
goog.require('om.core');
goog.require('om.core');
word_keeper.cljs.frontend.app_state = cljs.core.atom.call(null,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"text","text",-1790561697),"Hello Om!"], null));
word_keeper.cljs.frontend.widget = (function widget(data){if(typeof word_keeper.cljs.frontend.t5887 !== 'undefined')
{} else
{
/**
* @constructor
*/
word_keeper.cljs.frontend.t5887 = (function (data,widget,meta5888){
this.data = data;
this.widget = widget;
this.meta5888 = meta5888;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
word_keeper.cljs.frontend.t5887.cljs$lang$type = true;
word_keeper.cljs.frontend.t5887.cljs$lang$ctorStr = "word-keeper.cljs.frontend/t5887";
word_keeper.cljs.frontend.t5887.cljs$lang$ctorPrWriter = (function (this__4202__auto__,writer__4203__auto__,opt__4204__auto__){return cljs.core._write.call(null,writer__4203__auto__,"word-keeper.cljs.frontend/t5887");
});
word_keeper.cljs.frontend.t5887.prototype.om$core$IRender$ = true;
word_keeper.cljs.frontend.t5887.prototype.om$core$IRender$render$arity$1 = (function (this__5227__auto__){var self__ = this;
var this__5227__auto____$1 = this;return React.DOM.h1(null,new cljs.core.Keyword(null,"text","text",-1790561697).cljs$core$IFn$_invoke$arity$1(self__.data));
});
word_keeper.cljs.frontend.t5887.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_5889){var self__ = this;
var _5889__$1 = this;return self__.meta5888;
});
word_keeper.cljs.frontend.t5887.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_5889,meta5888__$1){var self__ = this;
var _5889__$1 = this;return (new word_keeper.cljs.frontend.t5887(self__.data,self__.widget,meta5888__$1));
});
word_keeper.cljs.frontend.__GT_t5887 = (function __GT_t5887(data__$1,widget__$1,meta5888){return (new word_keeper.cljs.frontend.t5887(data__$1,widget__$1,meta5888));
});
}
return (new word_keeper.cljs.frontend.t5887(data,widget,null));
});
om.core.root.call(null,word_keeper.cljs.frontend.widget,word_keeper.cljs.frontend.app_state,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"target","target",253001721),document.getElementById("omable")], null));
